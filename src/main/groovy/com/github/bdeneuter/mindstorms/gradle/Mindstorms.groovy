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
package com.github.bdeneuter.mindstorms.gradle

import org.gradle.api.AntBuilder
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

            project.task('setManifestAttributes') << {

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

            }

            jar.dependsOn setManifestAttributes

            project.task(dependsOn: 'assemble', group: 'mindstorms', 'copyToRobot') << {

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
                            todir: "${project.mindstorms.user}" + '@' + "${project.mindstorms.ip}" + ':' + "${project.mindstorms.home}",
                            username: "${project.mindstorms.user}",
                            password: "${project.mindstorms.password}",
                            trust: true
                    )
                }

            }

            project.task(dependsOn: 'copyToRobot', group: 'mindstorms', 'launch') << {

                ant.lifecycleLogLevel = AntBuilder.AntMessagePriority.INFO

                ant.taskdef(
                        name: 'sshexec',
                        classname: 'org.apache.tools.ant.taskdefs.optional.ssh.SSHExec',
                        classpath: configurations.scp.asPath)

                File file = new File(buildDir, 'libs')
                        .listFiles()
                        .find { it.name.endsWith '.jar' }
                String application = new File("${project.mindstorms.home}", file.getName()).absolutePath

                ant.sshexec(
                        username: "${project.mindstorms.user}",
                        password: "${project.mindstorms.password}",
                        host: "${project.mindstorms.ip}",
                        command: "jrun -cp $application ${project.mindstorms.main}",
                        trust: true
                )
            }

            project.task(group: 'mindstorms', 'halt') << {

                ant.taskdef(
                        name: 'sshexec',
                        classname: 'org.apache.tools.ant.taskdefs.optional.ssh.SSHExec',
                        classpath: configurations.scp.asPath)

                ant.sshexec(
                        username: "${project.mindstorms.user}",
                        password: "${project.mindstorms.password}",
                        host: "${project.mindstorms.ip}",
                        command: "halt",
                        trust: true
                )
            }

        }

    }
}


