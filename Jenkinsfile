reference = library identifier: 'jenkins-library-demo@master', retriever: modernSCM(
              [$class: 'GitSCMSource',
               remote: 'https://github.com/m41na/jenkins-library-demo.git,
               credentialsId: 'github-jenkins-token'])
pipeline {
    agent any

    tools {
        gradle 'gradle-6.8.3'
    }

    stages {
        stage ('Greet') {
            steps {
                sh "echo greetings"
                greet reference
            }
        }

        stage ('Clean') {
            steps{
                script {
                    sh "gradle clean build -x test"
                }
            }
        }
    }
}