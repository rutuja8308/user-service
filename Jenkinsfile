pipeline {
agent any

tools {
    jdk 'JDK21'
    maven 'MAVEN'
}

environment {

    TOMCAT_HOME = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1'
    WAR_FILE = 'user-service.war'

}

options {
    timestamps()
}

stages {

    stage('Build WAR') {
        steps {

            bat 'mvn clean install'

        }
    }

    stage('Stop Tomcat') {
        steps {

            bat '''
            cd /d "%TOMCAT_HOME%\\bin"
            shutdown.bat
            '''

        }
    }

    stage('Delete Old WAR') {
        steps {

            bat '''
            if exist "%TOMCAT_HOME%\\webapps\\%WAR_FILE%" (
                del /F /Q "%TOMCAT_HOME%\\webapps\\%WAR_FILE%"
            )
            '''

        }
    }

    stage('Deploy WAR') {
        steps {

            bat '''
            copy /Y "target\\%WAR_FILE%" "%TOMCAT_HOME%\\webapps\\%WAR_FILE%"
            '''

        }
    }

    stage('Start Tomcat') {
        steps {

            bat '''
            cd /d "%TOMCAT_HOME%\\bin"
            start startup.bat
            '''

        }
    }
}

post {

    success {

        echo 'Tomcat Deployment Successful'

    }

    failure {

        echo 'Tomcat Deployment Failed'

    }
}
}