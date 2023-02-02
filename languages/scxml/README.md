## SCXML language module

This language module enables the use of JPlag with SCXML submissions.
It works by first parsing the XML files using a SAX parser and transforming them into an intermediate Java object
structure. The tokens are extracted by iterating over this structure.

### Token Extraction

There are two token extraction strategies available: the SimpleStatechartTokenGenerator and the
DynamicStatechartTokenGenerator.

The SimpleStatechartTokenGenerator extracts tokens by recursively traversing the
Statechart object, using the elements outlined in the [SCXML specification](https://www.w3.org/TR/scxml).
The DynamicStatechartTokenGenerator utilizes a larger token set and extracts tokens based on the attributes of the
StatechartElement, for example extracting a `PARALLEL_STATE` token for the State object if it is parallel.

## Usage

To use the new module, add the `-l scxml` flag in the CLI.
