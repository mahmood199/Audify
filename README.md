# Future of the project
0) Media player using exo player
1) UI/UX improvement of media list screen.
2) UI/UX improvement of media player screen.
3) Adding support for music streaming.
4) Downloading music for playing later.
5) Adding favourites feature.
6) Sort files by artist, albums.
7) Giving smooth UI for editing music details.
8) Shuffle and replay same music.
9) Make personal playlist
10) Searching music locally.
11) Searching music from remote services.
12) Move to compose.
13) Added more interview quesions for services side by side.


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

## Modifying notification from code and its visial effect

### 1. LongText(BigText) Style and big context title.


<br>

![Screenshot 2023-03-01 015945](https://user-images.githubusercontent.com/58071934/221971843-34ffaef3-48ab-47d9-908f-fe6f4e5d1e58.png)

<br>

<br>

![WhatsApp Image 2023-03-01 at 1 58 03 AM](https://user-images.githubusercontent.com/58071934/221971911-cf920ee2-7fe7-48c7-9616-fda3851f853b.jpeg)

<br>

<br>

![WhatsApp Image 2023-03-01 at 1 58 03 AM (1)](https://user-images.githubusercontent.com/58071934/221972006-2e3a8aa2-d589-4a6a-8d39-300fa17e5014.jpeg)

<br>

### 2. For BigPicture Style and Media Style Notification - Refer this video https://www.youtube.com/watch?v=s0Q2QKZ4OP8&list=PLrnPJCHvNZuDR7-cBjRXssxYK0Y5EEKzr&index=5 

### 3. For Messaging and Direct Reply - Refer to this video https://www.youtube.com/watch?v=DsFYPTnCbs8&list=PLrnPJCHvNZuDR7-cBjRXssxYK0Y5EEKzr&index=7

*Note -* From API level 24 onwards notification from an app are grouped together if there are multiple of them. So it is handled automatically. 

*Note -* Multiple channels can be grouped under one channel group. If we don't put a channel in a group then by default it goes to other(default) channel.

*Note -* For custom layout notification, refer to this video https://www.youtube.com/watch?v=axcdnRAcqLw&list=PLrnPJCHvNZuDR7-cBjRXssxYK0Y5EEKzr&index=12 

<br>

More references for Music Player
https://github.com/topics/android-music-player?l=kotlin 

## LLD for media player that I have made
![image](https://user-images.githubusercontent.com/58071934/224479944-8c51d1b3-b389-4a00-be84-c2d2f65d4cbc.png)

## Services related android interview question resources
1. https://climbtheladder.com/android-service-interview-questions/
2. https://stacktips.com/articles/android-service-interview-questions
3. https://www.interviewbit.com/android-interview-questions/


