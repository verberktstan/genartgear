# genartgear

Some functions to ease the usage of Quil for Generative Art.

## Getting started

Copy `genartgear-0.1.4-SNAPSHOT.jar` to the :resource-paths of your Quil project. 

..or create a Jar with `lein uberjar` and copy the (non-standalone) jar to the :resource-paths of your Quil project.

## Usage

Require genartgear.core (alongside quil.core) in your namespace:

```(ns genartgear.example
  (:require [genartgear.core :as gg]
            [quil.core :as q]))```

Define a draw function for your sketch:

```(defn- draw
  [{:keys [gray] :or {gray 0}}]
  (q/background gray))```

Simply pass the draw function to the sketch macro:

```(gg/sketch draw)```

You can supply additional setup arguments like :title & :size

```(gg/sketch draw :title "another-sketch" :size [800 300])```

Arguments passed to the sketch macro will be passed on to your draw fn.

```(gg/sketch draw :gray 180)```

## License

Copyright © 2024 Stan Verberkt

Distributed under the MIT License.
