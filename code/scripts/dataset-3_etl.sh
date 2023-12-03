#!/bin/bash
# Owner: Wan-Yu Lin

#sbt clean
#sbt compile
#sbt package

hadoop fs -rm -r /user/wl2484_nyu_edu/project/data/clean/tlc/zone_lookup
hadoop fs -rm -r /user/wl2484_nyu_edu/project/data/profile/tlc/zone_lookup

spark-submit --deploy-mode cluster \
  --class etl.TaxiZones tlc-based-transport_2.12-0.3.0.jar \
  --source /user/wl2484_nyu_edu/project/data/source/tlc/zone_lookup \
  --clean-output /user/wl2484_nyu_edu/project/data/clean/tlc/zone_lookup \
  --profile-output /user/wl2484_nyu_edu/project/data/profile/tlc/zone_lookup