pipeline {
    agent any
    stages {
        stage('Build test code') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Execute test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Generate allure report') {
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk              : '',
                            properties       : [],
                            reportBuildPolicy: 'ALWAYS',
                            results          : [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }
}