# Baller for Android (Alpha 0.0.3)

Baller is a cross-platform View Framework.  It's not an Application framework, it's just a way to implement a view e.g. a "User Interface Screen".  

Baller Views are written in TypeScript.   The reuslting transpiled JavaScript file can be use in any of the existing Baller runtimes - currently iOS, Android and Web.  

Here's an overview of the functionality.

## View Types:

- Div: basic view container
- Field: text entry field
- Button: button
- Image: image
- Label: text 
- List: easy, but powerful (iOS uses a UICollectionView to implement this)
 
## Services:

- Http: for retrieval of JSON data
- Store: for easy, efficient storage/retrieval of JSON data


## API Documentation

Docs are coming soon.  For now, it's pretty easy to review the Classes and their APIs by looking at the core TypeScript implementation in the git repo [here](https://github.com/bradedelman/baller-core).

## Getting Started on Android
 
It's really easy to get up and running with Baller in any Kotlin Application.  Let's walk through the steps to create an Application from scratch:

1. Create a new Kotlin App
	- launch Android Studio
	- Create New Project (Empty Activity, Language: Kotlin, Name: HostApp)
	- Confirm you have a class named MainActivity with a method onCreate
	- Confirm it builds, runs in Simulator - you see a Hello World screen
2. Add the Baller Module to your App
	- The project has 2 gradle scripts that you'll add to:
    
	```
	build.gradle (Project: HostApp)
	
	in the allprojects repositories seciton, add the additional repository needed to
	get Baller from jitpack.io.
	
	line 21:
	
		maven { url 'https://jitpack.io' }
	```

    and
	    
	```
	build.gradle (Module: HostApp.app)
		
	in the dependencies section, we'll add Baller
		
	line 36: (the hex number is a git revsion)
		
	    implementation 'com.github.bradedelman:baller-android:0.0.3'
	```

	- Now you need to "Sync Gradle" - it will download, etc. 
	- Baller is now in your Project
	
3. Add Permission to Use the Internet and to use a local http server (for dev)

	```
	in AndroidManifest.xml
	
	add on line 4 (right before the <application	
	
		<uses-permission android:name="android.permission.INTERNET" />
		
	also add this attribuet to the application tag:
		
		android:usesCleartextTraffic="true"
	
	```
		

4. Add a Sample Baller View to your App
	- Open MainActivity
	- add an import at the top

	````
	import com.affirm.baller.platform.BallerView
	````
	
	- delete this line:

	```
	setContentView(R.layout.activity_main)
	```
	
	- and put this there instead:
	
	```
    val ballerView = BallerView(this, 320); // width is virtualized
    setContentView(ballerView);

    // load script (loadUrl is a convienence, but load can be used
    // for a script that comes from anywhere)
    ballerView.loadUrl("https://www.cleverfocus.com/baller/sample.js")	
	```

### That's it!  Run and you'll see the sample view with a scrolling list of 1,000 numbers!   More coming soon on how to create your own views.
