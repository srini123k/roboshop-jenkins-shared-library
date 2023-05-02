def call() {
    pipeline {
        agent any

        options {
            ansiColor('xterm')
        }

        parameters {
            string(name: 'app_version', defaultValue: '', description: 'App Version')
            string(name: 'component', defaultValue: '', description: 'Component')
            string(name: 'environment', defaultValue: '', description: 'Environment')
        }

        stages {
            stage('Clone Application') {
                steps {
                    dir('APP') {
                        git branch: 'main', url: "https://github.com/srini123k/${component}"
                    }
//                    dir('HELM') {
//                        git branch: 'main', url: "https://github.com/srini123k/roboshop-helm-chart"
//                    }
//                }
                }
                stage('Deploy Helm Chart') {
                    steps {
                        script {
                            sh 'helm install ${component} APP/helm/${environment}.yaml'
                            //sh 'helm upgrade -i ${component} HELM/ -f APP/helm/${environment}.yaml --set appversion=${app_version}'
                        }
                    }

                }
            }

            post {
                always {
                    cleanWs()
                }
            }

        }
    }

}