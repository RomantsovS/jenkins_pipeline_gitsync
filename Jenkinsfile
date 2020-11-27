pipeline {
    string(defaultValue: "${env.PLATFORM_1C_VERSION}", description: 'Версия платформы 1С', name: 'PLATFORM_1C_VERSION')
    string(defaultValue: "${env.ERROR_COMMENT}", description: 'Проверять комментарии к хранилищу', name: 'ERROR_COMMENT')
    string(defaultValue: "${env.GIT_REMOTE}", description: 'Выполнять push pull в удаленный репозиторий', name: 'GIT_REMOTE')
    string(defaultValue: "${env.EXTENSION_1C_NAME}", description: 'Имя расширения 1С', name: 'EXTENSION_1C_NAME')
    string(defaultValue: "${env.STORAGE_PATH}", description: 'Путь к хранилищу 1С', name: 'STORAGE_PATH')
    string(defaultValue: "${env.LOCAL_REPO_PATH}", description: 'Путь к локальному репозиторию', name: 'LOCAL_REPO_PATH')
    string(defaultValue: "${env.jenkinsAgent}", description: 'Нода дженкинса, на которой запускать пайплайн. По умолчанию master', name: 'jenkinsAgent')
    }

    agent {
        label "${(env.jenkinsAgent == null || env.jenkinsAgent == 'null') ? "master" : env.jenkinsAgent}"
    }

    options { 
        buildDiscarder(logRotator(numToKeepStr: '7'))
        timestamps()
        timeout(time: 2, unit: 'HOURS')
    }

    stages {
        stage("Init") {
            steps {
                script {
                    try { timeout(time: 5, unit: 'MINUTES') {
                        load "./SetEnvironmentVars.groovy"   // Загружаем переменные окружения (настойки)
                        commonMethods = load "./lib/CommonMethods.groovy" // Загружаем общий модуль
                    }}
                    catch (Throwable excp) {
                        error excp.message
                    }
                }
            }
        }

        stage('run gitsync') {
            steps {
                script {
                    try { timeout(time: env.TIMEOUT_FOR_COMLILE_TESTS_STAGE.toInteger(), unit: 'MINUTES') {
                        def ib_connection = "/S${env.SERVER_1C}:${env.CLUSTER_1C_PORT}\\${env.TEST_BASE_NAME}"
                        
                        def command = "gitsync --v8version ${params.PLATFORM_1C_VERSION} --verbose sync --limit 1 ${params.STORAGE_PATH} ${params.LOCAL_REPO_PATH}"

                        if(params.ERROR_COMMENT) {
                            command = command + " --error-comment"
                        }

                        if(params.GIT_REMOTE) {
                            command = command + " --pull --push"
                        }

                        if(params.EXTENSION_1C_NAME) {
                            command = command + " --extension PDE ${params.EXTENSION_1C_NAME}"
                        } 

                        returnCode = commonMethods.cmdReturnStatusCode(command)
    
                        echo "cmd status code $returnCode"
    
                        if (returnCode != 0) {
                            commonMethods.echoAndError("Error running gitsync ${STORAGE_PATH} at ${LOCAL_REPO_PATH}")
                        }
                    }}
                    catch (Throwable excp) {
                        error excp.message
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                commonMethods.emailJobStatus("BUILD STATUS")
            }
        }
    }
}