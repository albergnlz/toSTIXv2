#!/bin/bash  
python isc2stix.py > stix.xml
stix2_elevator stix.xml > 1.json
