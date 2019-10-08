# Package Challenging API
This project aims to create Package Challenging from the given input package parameters.
Each thing inside the package has such parameters as index number, weight and cost. The
package has a weight limit. 

Application goal is to determine which things to put into the package so that the
total weight is less than or equal to the package limit and the total cost is as large as possible.
Also the package which weights less is better than if there is more than one package with the
same price.

Version:package-0.0.1-SNAPSHOT

# Installation
-Package Challenging is a maven Application project written in Java 8.
-Simply run the Packer to start the application.
-No database connection required

# Usage

Project has one main method which runs all the algorithm within.

Additional constraints:
1. Max weight that a package can take is ≤ 100
2. There might be up to 15 items you need to choose from
3. Max weight and cost of an item is ≤ 100

# Authentication

No Authentication is required. 

# Data Format
The input text file must contain a limit in each line and items defined in "( )" - bracers.

$MaxWeightLimit: (itemANumber,itemAWeight,itemACost)  (itemBNumber,itemBWeight,itemBCost)

Example
44 : (1,24.31,€33) (2,14.55,€56) (3,30.91,€14) (4,16.13,€58)

# Credits
github to give a facility to share this code to all. 
