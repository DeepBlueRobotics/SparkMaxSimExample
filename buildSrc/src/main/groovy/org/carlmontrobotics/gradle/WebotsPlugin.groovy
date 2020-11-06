package org.carlmontrobotics.gradle

import groovy.transform.CompileStatic
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Dependency
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.internal.os.OperatingSystem
import org.gradle.jvm.tasks.Jar
import org.gradle.api.tasks.StopActionException
import org.slf4j.LoggerFactory

@CompileStatic
class WebotsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def log = LoggerFactory.getLogger('webots-plugin-logger')
        def os = OperatingSystem.current()
        String envDelim = os.isWindows() ? ";" : ":"
        def webots_home = ""
        List<String> dirs_to_check = new ArrayList<String>()

        def webots_home_env = System.getenv("WEBOTS_HOME")
        if (webots_home_env) {
            dirs_to_check.add(webots_home_env)
        }

        if (dirs_to_check.size() == 0) {
            log.info "WEBOTS_HOME environment variable is not set, so trying to set it automatically."
            if (os.isLinux()) {
                dirs_to_check.add "/usr/local/webots"
            } else if (os.isMacOsX()) {
                dirs_to_check.add "/Applications/Webots.app"
            } else if (os.isWindows()) {
                dirs_to_check.add System.getenv("USER_HOME") + "/Webots"
                dirs_to_check.add System.getenv("LOCALAPPDATA") + "/Programs/Webots"
            }
        }
        for (d in dirs_to_check) {
            log.info "Checking '" + d + "'"

            def f = new File(d)
            if (f.exists()) {
                log.info "Found it!"
                webots_home = d
                break
            }
        }

        if (webots_home == "") {
            log.warn "Can't find Webots installation directory. Install Webots and/or set WEBOTS_HOME environment variable for Webots support."
            return
        }

        def ldpath = webots_home + "/lib/controller"
        project.repositories.flatDir([ dirs: ldpath+"/java"])
        def controllerJar = project.files(ldpath+"/java/Controller.jar")
        project.dependencies.add("compile", controllerJar)

        def libs = project.files(project.fileTree(ldpath).getFiles())
        project.dependencies.add("nativeDesktopLib", libs)
        
        if (os.isWindows()) {
            def mingw64libs = project.files(project.fileTree(webots_home+"/msys64/mingw64/bin").getFiles())
            def mingw64cpplibs = project.files(project.fileTree(webots_home+"/msys64/mingw64/bin/cpp").getFiles())
            project.dependencies.add("nativeDesktopLib", mingw64libs)
            project.dependencies.add("nativeDesktopLib", mingw64cpplibs)
        }
    }
}