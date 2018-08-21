pipeline {
    agent {
        label 'Slave_Induccion'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        disableConcurrentBuilds()
    }
    tools {
        jdk 'JDK8_Centos'
        gradle 'Gradle4.5_Centos'
    }
    stages {
        stage('Checkout') {
            steps {
                echo "------------>Checkout<------------"
                checkout([$class                           : 'GitSCM',
                          branches                         : [[name: 'develop']],
                          doGenerateSubmoduleConfigurations: false,
                          extensions                       : [],
                          gitTool                          : 'Git_Centos',
                          submoduleCfg                     : [],
                          userRemoteConfigs                : [[credentialsId: 'GitHub_jpoh97',
                          url                              : 'https://github.com/jpoh97/ParkingLot']]])
            }
        }
        stage('Compile') {
			steps{
				echo "------------>Compile<------------"
				sh 'gradle --b ./parkinglot-backend/build.gradle compileJava'
                sh 'gradle --b ./parkinglot-backend/build.gradle clean'
			}
		}
        stage('Unit Tests') {
            steps {
                echo "------------>Unit Tests<------------"
                sh 'gradle --b ./parkinglot-backend/build.gradle test'
                junit '**/build/test-results/test/*.xml'             
            }
        }
        stage('Static Code Analysis') {
            steps {
                echo '------------>Static Code Analysis<------------'
                withSonarQubeEnv('Sonar') {
                    sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
                }
            }
        }
        stage('Build') {
            steps {
                echo "------------>Build<------------"
                sh 'gradle --b ./parkinglot-backend/build.gradle build -x test'
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
            junit '**/parkinglot-backend/build/test-results/test/*.xml'
        }
        failure {
            echo 'This will run only if failed'
            mail(to: 'juan.ospina@ceiba.com.co',
                    subject: "Failed Pipeline:${currentBuild.fullDisplayName}",
                    body: "Something is wrong with ${env.BUILD_URL}")
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}