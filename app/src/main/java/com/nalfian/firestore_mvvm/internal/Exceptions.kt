package com.nalfian.firestore_mvvm.internal

import java.io.IOException

class NoConnectivityException: IOException()
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()