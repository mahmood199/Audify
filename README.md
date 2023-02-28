# Service

Three types of services
 - ForeGround
 - Background
 - Intent Service - creates a new thread for execution of task on backgroud. we don't have to manage creating new thread. intent services works the same way like normal service if it is bound. Functionality wise it remains the same as service.
 - Bound

<br>
Two ways to start a STARTED Services <br/>
1) startService() method <br/>
2) startForegroundService() method <br/>
<br>

<br>
Two ways to start a BOUND Services <br/>
1) call startService()/startForegroundService() method and then bind client to it. <br/>
2) directly binding client to service using bindService() method. <br/>
<br>



LifeCycle methods of services
1) onStartCommand 
2) onBind 
3) onUnbind 
4) onRebind 
5) onCreate 
6) onDestroy 
7) onTaskRemoved() <-- called when application is removed from recents tab. Call stopSelf() here.

We must explicitly set the service in the intent.

If we start a service by just calling bindService() then it will be destroyed when its clients are destroyed.
But is we start by using startService() or startForegroundService() then it will be killed by stopSelf only.


By default service runs on main thread.
https://developerlife.com/2017/07/10/android-o-n-and-below-component-lifecycles-and-background-tasks/


Read about how services can be restarted using some contents.
START_STICKY
START_NOT_STICKY
START_REDELIVER_INTENT 


Service which provides information to activity or another service is called bound service.
If the service is bound to the components of the same app, then it is local binding. With other app it becomes remote binding.this remote binding can be though of as IPC (Inter Process Communication)

Two types of bound services
Local binding - implemented using IBinder  interface.
Remote binding - implemented using 
Messenger API - 
AIDL (Android Interface Definition language) (Not recommended to use)


Local Binding 
A bound service cannot be stopped. We need to unbind it first.
If we try to stop an unbound service it wont stop. But as soon as its unbounded itll be stopped and destroyed.
OnBind is only called once to bind the service. Later calls are ignored

<br>

# Notification

*Note -* A channel is needed to show notifications. If we try to send notification before creating channel we won't be able to see the notification. because it won't even trigger.