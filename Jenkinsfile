pipeline {
    agent any

    tools {
        gradle 'gradle-6.8.3'
    }

    stages {
        stage ('Clean') {
            steps{
                script {
                    sh "gradle clean"
                }
            }
        }
    }
}