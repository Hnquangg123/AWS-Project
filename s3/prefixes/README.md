
## Create bucket
```sh
aws s3 mb s3://prefixes-dylan-225
```

## Create folder
```sh
aws s3api put-object --bucket="prefixes-dylan-225" --key="hello/"
```


## Create many folders
```sh
aws s3api put-object --bucket="prefixes-dylan-225" --key="Lorem/ipsum/dolor/sit/amet,/consectetur/adipiscing/elit./Pellentesque/tristique/urna/sollicitudin/tellus/posuere/consequat./Nam/maximus/vestibulum/lacus./Vestibulum/consectetur/ipsum/non/erat/mattis,/vitae/eleifend/libero/interdum./Integer/in/turpis/tellus./Suspendisse/potenti./Quisque/euismod/lorem/sit/amet/nisi/dictum/semper./Curabitur/nec/eros/elementum,/porta/quam/sed,/vehicula/diam./Maecenas/dictum,/libero/vitae/commodo/pellentesque,/eros/dolor/tincidunt/justo,/eget/vestibulum/neque/diam/id/enim./Proin/at/congue/justo,/ut/pellentesque/est./Sed/ac/ornare/orci./Proin/tincidunt/ante/ac/lectus/fermentum,/vel/convallis/dui/blandit./Morbi/tincidunt/magna/fringilla/nulla/hendrerit,/eget/vestibulum/turpis/pretium./Suspendisse/non/sodales/sem,/vitae/dictum/arcu./Ut/vitae/dignissim/sapien./Fusce/eget/erat/in/quam/vulputate/pretium/facilisis/a/massa./In/vitae/condimentum/elit./Donec/eleifend,/nisi/ut/vehicula/pulvinar,/arcu/sem/tempus/velit,/ut/vestibulum/nibh/augue/quis/dolor./Nulla/pretium/lorem/at/neque/accumsan"
```

## Create too long prefixes
```sh
aws s3api put-object --bucket="prefixes-dylan-225" --key="Lorem/ipsum/dolor/sit/amet,/consectetur/adipiscing/elit./Pellentesque/tristique/urna/sollicitudin/tellus/posuere/consequat./Nam/maximus/vestibulum/lacus./Vestibulum/consectetur/ipsum/non/erat/mattis,/vitae/eleifend/libero/interdum./Integer/in/turpis/tellus./Suspendisse/potenti./Quisque/euismod/lorem/sit/amet/nisi/dictum/semper./Curabitur/nec/eros/elementum,/porta/quam/sed,/vehicula/diam./Maecenas/dictum,/libero/vitae/commodo/pellentesque,/eros/dolor/tincidunt/justo,/eget/vestibulum/neque/diam/id/enim./Proin/at/congue/justo,/ut/pellentesque/est./Sed/ac/ornare/orci./Proin/tincidunt/ante/ac/lectus/fermentum,/vel/convallis/dui/blandit./Morbi/tincidunt/magna/fringilla/nulla/hendrerit,/eget/vestibulum/turpis/pretium./Suspendisse/non/sodales/sem,/vitae/dictum/arcu./Ut/vitae/dignissim/sapien./Fusce/eget/erat/in/quam/vulputate/pretium/facilisis/a/massa./In/vitae/condimentum/elit./Donec/eleifend,/nisi/ut/vehicula/pulvinar,/arcu/sem/tempus/velit,/ut/vestibulum/nibh/augue/quis/dolor./Nulla/pretium/lorem/at/neque/accumsan/thisonewillerror"
```
