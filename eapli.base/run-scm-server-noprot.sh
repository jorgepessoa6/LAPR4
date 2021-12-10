#!/usr/bin/env bash

export BASE_CP=base.app.scm/target/base.app.scm-1.3.0-SNAPSHOT.jar:base.app.scm/target/dependency/*;
java -cp $BASE_CP base.app.scm.run.CollectMachineRequestsSMNoProt