env.VERBOSE = "true"
env.EMAIL_ADDRESS_FOR_NOTIFICATIONS = "romantsov_s@rusklimat.ru"

if (isUnix()) {
    env.INSTALLATION_DIR_1C = "/opt/1C/v8.3/x86_64"
    env.THICK_CLIENT_1C = env.INSTALLATION_DIR_1C + "/1cv8"
    env.THICK_CLIENT_1C_FOR_STORAGE = env.THICK_CLIENT_1C
    // env.ONE_SCRIPT_PATH="/usr/bin/oscript"
} else {
    env.PLATFORM_1C_VERSION = "8.3.12"
    env.PATH_TO_1C = "" //"\\\\rusklimat.ru\\app\\1Cv8ADM\\8.3.12.1685_CR\\bin\\1cv8.exe"
    env.RAC_PATH = "\\\\rusklimat.ru\\app\\1Cv8ADM\\8.3.16.1063\\bin\\rac.exe"
}