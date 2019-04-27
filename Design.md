***Game concept***
-
**2 modes:**
- 3D
- 4D

**3 platonic solids**
- tetraedr (triangular)
- cube (square)
- pentagonal


***Data presenatation***
-
*Physical representation:*

 solid >> face >> center + lines >> bits

*Abstract representation:*

 Graph 1 (bidirectional multigraph):
  - vertices = faces (with value of color or else ID)
  - edges connect vertices. the value is line of bits on soure vertice 
  ```example
  connecting faces(vertices) A and B:
  value of A->B is line of bits on face A
  value of A<-B if line of bits on face B
  ```
  
 Graph 2  <for 4D mode>:
  - vertices = 3D solids
  - edges = 2D common faces

***Architectural ideas***
-
**MVVM approach:**

***Model:***

Generates initial state.
Processes change-commands to state.
Always return current state.

***ViewModel:***

Stores model-state.
Stores view-state. Decides if change to model-state is necessary. 
Stores input-state. Listen to changes. Updates view-state.


***View:***
 Renders graphics (from view-state).
 Listens user input (pushes to input-state).




***Visual implementation steps***
-
- Simple visualization of cube
Two dimensional plane. 6 squares of cube with simple controls
```
square (3x3)    
 <-  -> 
 
square (3x3)    
 <-  -> 
 
square (3x3)    
 <-  -> 
 
square (3x3)    
 <-  -> 
 
square (3x3)    
 <-  -> 
 
square (3x3)    
 <-  -> 
 ```

- 3D visualization with rapid rotation

- 3D vis. with SMOOTH revolving

- unfolded 4D visualization. bunch of 3d solids with ability to switch between 