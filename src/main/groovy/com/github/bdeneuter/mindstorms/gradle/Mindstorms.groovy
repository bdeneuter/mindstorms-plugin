/*
 * Copyright 2016 Bart De Neuter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.bdeneuter.mindstorms.gradle;

import org.gradle.api.Plugin
import org.gradle.api.Project

class Mindstorms implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("mindstorms", MindstormsExtension)
        project.configure(project) {

            apply plugin: 'java'

            sourceCompatibility = 1.8
            targetCompatibility = 1.8

            repositories {
                mavenCentral()
            }

            configurations {
                scp
            }

            dependencies {
                compileOnly 'com.github.bdeneuter:lejos-ev3-api:0.9.1-beta'
                scp 'org.apache.ant:ant-jsch:1.9.4'
            }

            jar {

                from {
                    (configurations.runtime).collect {
                        it.isDirectory() ? it : zipTree(it)
                    }
                }

                manifest {
                    attributes('Class-Path':
                            'home/root/lejos/lib/ev3classes.jar ' +
                                    '/home/root/lejos/lib/dbusjava.jar ' +
                                    '/home/root/lejos/libjna/usr/share/java/jna.jar',
                            'Main-Class': "${project.mindstorms.main}")
                }
            }

            project.task(dependsOn: 'assemble', 'copyToRobot') << {

                ant.taskdef(
                        name: 'scp',
                        classname: 'org.apache.tools.ant.taskdefs.optional.ssh.Scp',
                        classpath: configurations.scp.asPath)

                new File(buildDir, 'libs')
                        .listFiles()
                        .findAll { it.name.endsWith '.jar' }
                        .each {
                    ant.scp(
                            file: it,
                            todir: brick_user + '@' + brick_host + ':' + brick_home,
                            username: brick_user,
                            password: '',
                            trust: true
                    )
                }

            }

        }

    }
}

