/*
 * Copyright 2017 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.devsoap.plugin.tasks

import com.devsoap.plugin.ProjectType
import com.devsoap.plugin.Util
import org.gradle.api.tasks.bundling.Zip

/**
 * Creates a Zip archive that is compatible with Vaadin Directory
 *
 * @author John Ahlroos
 * @since 1.0
 */
class CreateDirectoryZipTask extends Zip {

    static final String NAME = 'vaadinAddonZip'

    CreateDirectoryZipTask() {
        description = 'Creates an addon Zip archive compatible with Vaadin Directory.'

        // Zip includes addon jar + sources + javadoc jars
        from ({
            if(Util.getProjectType(project) == ProjectType.JAVA) {
                [project.tasks[BuildJavadocJarTask.NAME], project.tasks[BuildSourcesJarTask.NAME], project.tasks.jar]
            } else {
                [project.tasks[BuildSourcesJarTask.NAME], project.tasks.jar]
            }
        }) { into'libs' }

        // Include javadoc as files
        from ({
            if (Util.getProjectType(project) == ProjectType.JAVA) {
                return project.tasks.javadoc.destinationDir
            }
        }) { into 'javadoc'}

        // Include metadata
        from('build/tmp/zip')
    }
}
