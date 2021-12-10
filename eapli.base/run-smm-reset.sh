export BASE_CP=base.app.smm/target/base.app.smm-1.3.0-SNAPSHOT.jar:base.app.smm/target/dependency/*;
java -cp $BASE_CP base.app.smm.run.BaseSmmReset $1

