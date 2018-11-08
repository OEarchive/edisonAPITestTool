#OptiCx API GUI Testing Tool

##This tool exercises the OpticCx APIs  

### To Run

1. Clone this repo
1. Follow dev guidelines to install maven, etc.
1. Create an environment variable "PUSH_CONFIG_DIR" pointing to the config directory, e.g.,
```
vi ~/.bashrc and add the following 
export PUSH_CONFIG_DIR="/Users/<yourUsername>/Desktop/sources/oe-test/oe-test-opticxapi-gui/config"
```
1. Optional: If not on omnibus, in order to facilitate determining the rules <--> points mapping, 
open a VPN connection to bastion (or just ignore the connection errors).
1. mvn clean && mvn package -DskipTests (the latter to not run integration tests (requires Omnibus))
1. java -jar target/opticx-api-gui-1.0-SNAPSHOT.jar

### Undiscoverable usage tips:
1. To get to info on a specific site, d-click the site on the sites tab or select the customer on the customer
tab and then hit the query button on the sites tab (then double click the site).
