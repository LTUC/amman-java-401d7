# Prework: Computer Setup & Installation

### Operating Systems

This course supports Mac, Ubuntu and Windows users. All users must use the standard setup for their system, as described in the Code 201 & 301 prework. In particuluar, if you use Windows, make sure you have the Windows Subsystem for Linux installed & set up as described in [this guide](https://github.com/michaeltreat/Windows-Subsystem-For-Linux-Setup-Guide){:target="_blank"}.

### Command Line Setup

**For WSL users**, please ensure that you've set up the WSL as described in the above guide. Also, please run `sudo dpkg-reconfigure tzdata` to pick your current timezone in Ubuntu.

### Editors and IDEs

We'll use a variety of text editors and IDEs to build our projects throughout this course. We use **Visual Studio Code** for light text editing, **IntelliJ IDEA** for pure-Java programs and **Android Studio** when we're building Android applications. Additionally, you'll need to make sure **Java** itself is installed, as well as **Gradle**, a build system for Java applications.

Download and install the following on your base OS:

### Option 1 - Manual install Intellij and Android Studio

- [Android Studio](https://developer.android.com/studio){:target="_blank"}
- IntelliJ for [Windows](https://www.jetbrains.com/idea/download/#section=windows), [Mac](https://www.jetbrains.com/idea/download/#section=mac), [Linux](https://www.jetbrains.com/idea/download/#section=linux)

### Option 2 - Intellij with Toolbox (comes with Intellij and Android studio)
- [Install ToolBox by Jetbrains](https://www.jetbrains.com/lp/toolbox/)
- Toolbox can now install all Android studio and Intellij

### Set up your Computer!

- [Postrgres (EDB Installer)](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads){:target="_blank"}
- [Java - Latest (Adoptium Installer)](https://adoptium.net/){:target="_blank"}
  - Windows Users:
    - [Manual Install Gradle and follow Windows guide to install](https://gradle.org/install/){:target="_blank"}
    - Install [GitBash](https://gitforwindows.org/){:target="_blank" or [Cmder](https://cmder.app/){:target="_blank"} (linux style terminals) OR use Powershell going forward!
    - Install Git for Windows using Powershell: ```winget install --id Git.Git -e --source winget```. If this command doesn't work then download [App Installer from the windows store](https://apps.microsoft.com/store/detail/app-installer/9NBLGGH4NNS1?hl=en-us&gl=us&rtc=1) then try command again.
    - Alternative: Paid Intellij users may use WSL in Remote Development, however, it is in beta and free trial ends in 1 month!
  - Mac users: run `brew install gradle`.
  - Linux users: use the [manual installation instructions](https://gradle.org/install/#manually){:target="_blank"}.

Check to see if install was successful!
``` gradle --version ```
``` java --version ```
``` git --version ```



## Submission

For this assignment, turn in a screenshot of your terminal, where you've run the following commands in order. (You can copy-paste this entire chunk into your terminal.) If any of the output is different from the comment at the end of the line, then something has gone wrong in your installation, and you should reach out to your instructor for help.

Also turn in a screenshot in which you've opened IntelliJ.

```bash
mkdir setup-verification
cd setup-verification
gradle init --type java-library # select the default value for each prompt, should run some stuff and eventually give a success message
ls lib/src/main/java # should say setup/ or Library.java depending on Gradle version
./gradlew test # should say that all tests pass
javac -version # should say a version number
```
