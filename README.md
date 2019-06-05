# OptiCx API Testing Tool

## This tool exercises the OpticCx & Tesla APIs

### Dependencies

On osx these tools are likely already installed.

* Apache Maven
  * Check with `which mvn`

* Java Runtime Environment (JRE)
  * Check with `java --version`

### To Run

1. Clone this repo
2. Check dependencies, follow dev guidelines to install maven, java.
3. Execute: `mvn clean && mvn package -DskipTests`
4. Execute: `java -jar target/opticx-api-gui-1.0-SNAPSHOT.jar`



Tool should now be running. Be careful with this tool, it's a powerful mechanism to cause sweeping database changes.

### Configure Tool

First are you sure you know what you are doing?

If you are unsure, check with Hal about your attempted action. This tool is a sharp edge that can cause issues if you don't understand the outcomes.

TODO: Add details about how to set up configure the tool.
