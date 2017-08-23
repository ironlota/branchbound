# Branch Bound

TSP Solution with Branch and Bound approach using :
- Reduce Cost Matrix
- Completed Tour

## Getting Started

These application is requirements for Algortihm Strategy class.

Feature :
- Simulating best path
- Screenshot graph to external file in folder "./screenshot"
- Print Weight Matrix in Table

## Screenshot
![Graph1](https://raw.githubusercontent.com/rayandrews/branchbound/master/image/ss1.png)
![WeightMatrix](https://raw.githubusercontent.com/rayandrews/branchbound/master/image/ss2.png)
![Graph2](https://raw.githubusercontent.com/rayandrews/branchbound/master/image/ss3.png)

### Prerequisites

What things you need to install the software and how to install them

```
Installed :
- Java Development Kit (version > 7.0)
- Java IDE (Intellij, Netbeans, or Eclipse)
```

What things you need to include in project :

```
Libraries :
- Graphstream - [library for Graph]
- JTattoo     - [library for Eyecandy]
```

### Installing

```
wget [https://github.com/rayandrews/branchbound/archive/master.zip](https://github.com/rayandrews/branchbound/archive/master.zip)
unzip master.zip
cd branchbound-master
```

### How to make a matrix file for TSP
- Create new file in txt like this [99999 stands for infinite, it will block the edge creation in same node]

    For reduced cost matrix :
    ```
        ReducedCostMatrix

        5 X 5

        99999 20 30 10 11

        15 99999 16 4 2

        3 5 99999 2 4

        19 6 18 99999 3

        16 4 7 16 99999
    ```

    For completed tour :
    ```
        CompletedTour

        4 X 4

        99999 12 10 5

        12 99999 9 8

        10 9 99999 15

        5 8 15 99999
    ```

- Save as txt file in directory "./data/testcase"
- Then, open MainClassForm.java
- Find this code on line 82 [0 for Reduce Cost Matrix and 1 for Completed Tour]
```
 [line 82] typeOfGraph.setSelectedIndex(1); // change 0 or 1
```
- Change this code too if you make new file in directory "./data/testcase"
```
 [line 95]   if (typeOfGraph.getSelectedItem().equals("Reduced Cost Matrix")) {
 [line 96]        selectedFile = new File("./data/testcase/rcm_tc3.txt"); // change the path if you choose 0
 [line 97]        graphParser = new ReducedCostMatrix(selectedFile);
 [line 98]      } else {
 [line 99]        selectedFile = new File("./data/testcase/ct_tc4.txt"); // change the path if you choose 1
 [line 100]       graphParser = new CompletedTour(selectedFile);
 [line 101]  }
```

## To do and Bug
- JFileChooser and JComboBox usable but not functional (still fighting with thread)
- Completed GUI still in WIP

## Credits
- [Graphstream](https://github.com/graphstream) [gs-core and gs-ui needed]
- [JTattoo](http://www.jtattoo.net/) [for Eyecandy]

## Version

1.0 Major Release

## Authors

* **Ray Andrew** - [Ray Andrew](https://github.com/rayandrews)

## License

This project is licensed under the MIT License - see the [LICENSE.MD](LICENSE.MD) file for details
