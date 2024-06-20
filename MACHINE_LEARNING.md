![Heavy Inventories Machine Learning Banner](https://i.ibb.co/G7CDdTS/hi-ml.png)

# Heavy Inventories Machine Learning
Heavy Inventories runs based off of generated data, there are no hard coded weights. To do this, a machine learning 
algorithm is implemented to create the best possible/plausible weight.

## How?
On initialization, each item found in the Item registry will be given a double weight of 0.1D. Data from the Item Registry
is used to create weight files.

![Machine Learning Model](https://i.ibb.co/jr1FfQC/hi-ml-drawio.png)

## Recipe Finder
The recipe finder finds possible recipes of an item and creates an items weight based on materials used to create the item.
For example, a stone pickaxe consists of 3 cobblestone and 2 sticks. Each item's weight will be found and then combined together.
The texture of the result item will then be taken into account and a like-color ratio will be made.

### Color Ratio
A Color Ratio is calculated by finding similar pixels compared to the size of the image width and height. If multiple colors
are found, an array of Color Ratios will be returned.

## Figure Material
Figure Material is when an items material is taken into account, for instance, wood and stone have different densities,
therefore an items pixel size and its majority material makeup would result in mostly stone, creating a heavier weight.

### Can these values be overridden?
Yes, all machine created values are only for the base generated files. Any pre-made / changed values will be used in place
of the machines values.