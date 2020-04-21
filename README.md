# Sample AEM project template

This is a project template for AEM-based applications. It is intended as a best-practice set of examples as well as a potential starting point to develop your own functionality.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs as well as Hobbes-tests
* ui.content: contains sample content using the components from the ui.apps
* ui.tests: Java bundle containing JUnit tests that are executed server-side. This bundle is not to be deployed onto production.
* ui.launcher: contains glue code that deploys the ui.tests bundle (and dependent bundles) to the server and triggers the remote JUnit execution
* ui.frontend: an optional dedicated front-end build mechanism based on Webpack

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with

    mvn clean install -PautoInstallPackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish

Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
	
## Create System User

System users help us play with the aem reources, make changes and commmit it programmatically. Creating a system user involves couple of steps, the same is illustrated below.

Step 1:: Access crx explorer from the link http://localhost:4502/crx/explorer/index.jsp.

Step 2:: In order to create system user, you should have logged in with the Administrators credentials. Click on login on the crx explorer dashboard.

Step 3:: Click the option called "User Administartion". This now opens up a new window , Click "Create system user" to create one.

Step 4::  Fill the details :: User iD -> system (tagQueryService).
Enter the intermediate path: /home/users/system

Step 5:: Select the small green tick to complete the step.

As a pre-requisite lets first add permission to our tagQueryService . Lets consider we want this system user for content editing usage.

Step 1:: Navigate to User Admin Console.
Step 2:: Search for your user (tagQueryService).
Step 3:: Select your user and go to Permissions Tab.
Step 4:: Provide full access to /content folder. By selecting all check box against content row. You can ignore replicate checkbox as we don’t want this user for replication.
Step 5:: Click on save.

