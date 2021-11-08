reference = library identifier: 'jenkins-library-demo@master', retriever: modernSCM(
              [$class: 'GitSCMSource',
              remote: 'https://github.com/m41na/jenkins-library-demo.git',
              credentialsId: 'github-jenkins-token'])

// reference = library 'jenkins-library-demo@master'

pipeline {
    agent any

    tools {
        gradle 'gradle-6.8.3'
    }

    stages {
        stage ('Greet') {
            steps {
                script{
                    sh "echo Greetings"
                }
                greet(reference, "from $WORKSPACE")
            }
        }

        stage ('Library Build') {
            steps{
                script{
                    sh "echo Build using library"
                }
                buildProject(reference, this, "clean build -x test")
            }
        }
    }
}