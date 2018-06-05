pipeline {
  agent any
  triggers {
    pollSCM('*/1 * * * *')
  }
  tools {
    maven 'mvn3'
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }

  stages {
    stage('Sphinx') {
      agent {
          dockerfile {
            filename 'Dockerfile.build'
          }
      }
      steps {
        sh 'cd doc; make html'
      }
      post {
        always {
          archiveArtifacts 'doc/_build/**'
          step([
              $class: 'WarningsPublisher',
              consoleParsers: [
                  [parserName: 'Sphinx-build'],
              ],
              canComputeNew: false,
              unstableTotalAll: '0'
          ])
        }
      }
    }
    stage('Unit test') {
      steps {
        sh 'cd samples; mvn test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
        success {
          slackSend message: "test succes - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'good'
        }
        failure {
          slackSend message: "test failed - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'danger'
        }
      }
    }
  }
}