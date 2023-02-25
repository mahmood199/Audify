# Service

Three types of services
 - ForeGround
 - Background
 - Bound


LifeCycle methods of services
1) onStartCommand 
2) onBind 
3) onUnbind 
4) onRebind 
5) onCreate 
6) onDestroy 

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
