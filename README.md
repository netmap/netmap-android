# NetMap Game Client for Android

This repository contains the code for the NetMap android client.


## Setup

The steps below


### Android SDK Setup

1. Go to the [Eclipse Downloads page](http://www.eclipse.org/downloads/) and
   get the `Eclipse Classic` build.

2. Add the following lines to `eclipse.ini`.

    ```
    -vmargs
    -Xms128m
    -Xmx1024m
    ```

3. Go to the
   [Android SDK download page](http://developer.android.com/sdk/index.html) and
   choose `Use an existing IDE` then download the SDK.

4. Extract the SDK in `~/android` and run `~/android/tools/android`. Download
   everything that is compatible with your OS under these folders.
    * Tools
    * Android 4.2.2 (API 17)
    * Android 4.0 (API 14)
    * Extras

5. Follow
   [these instructions](http://developer.android.com/sdk/installing/installing-adt.html) to install
   [the Android Developer Tools plug-in](http://developer.android.com/tools/index.html).

6. When Eclipse restarts, ADT will ask you for an SDK path. Point it to
   `~/android/`, which contains the SDK that you have already set up.

7. Follow [these instructions](http://developer.android.com/tools/device.html)
   for setting up your Android hardware for development.

8. On Linux, also follow the _udev_ instructions in the Android documentation
   above. Although the Android docs refer to _Ubuntu Linux_, the instructions
   are applicable to all modern Linux distributions.

### Code

1. Clone [the ChromeView repository](https://github.com/pwnall/chromeview) in
   your Eclipse workspace.

    ```bash
    cd ~/workspace
    git clone git://github.com/pwnall/chromeview.git
    ```

2. Clone this repository in your Eclipse workspace.

    ```bash
    cd ~/workspace
    git clone git@github.com:netmap/netmap-android.git
    ```

3. Import the projects into Eclipse.

    * Go to the Eclipse menu, and select `File` > `Import`
    * Select `General` > `Existing Projects into Workspace`.
    * Click `Browse...` next to `Select root directory`.
    * Your workspace directory should be automatically selected. Click `OK`.
    * Click `Finish`.


## Development


