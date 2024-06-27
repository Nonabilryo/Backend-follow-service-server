package nonablii.followserviceserver.util.error

class CustomError(val reason: ErrorState): RuntimeException(reason.message)