# jackson-dataformat-toml
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fjustdb%2Fjackson-dataformat-toml.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fjustdb%2Fjackson-dataformat-toml?ref=badge_shield)

jackson dataformat for toml. The reader is based on [tomlj](https://github.com/tomlj/tomlj) and the writer is based on javascript toml parser [@iarna/toml](https://www.npmjs.com/package/@iarna/toml).  

** TOML 0.5.0 **

## Get it!
# Maven

Functionality of this package can be used using following Maven dependency:
```
<dependencies>
  ...
  <dependency>
    <groupId>com.teesoft</groupId>
    <artifactId>jackson-dataformat-toml</artifactId>
    <version>1.0</version>
  </dependency>
  ...
</dependencies>
```

## Use it!
With simple 2-property POJO like this:
```
// Note: can use getters/setters as well; here we just use public fields directly:
public class MyValue {
  public String name;
  public int age;
  // NOTE: if using getters/setters, can keep fields `protected` or `private`
}
```
```
TOMLMapper mapper = new TOMLMapper();
MyValue value = new MyValue();
value.name = "justdb";
value.age = 1;
String toml = mapper.writeValueAsString(value);
mapper.writeValue(new File("target/test.toml"), value);
MyValue valueNew = mapper.readValue(toml, MyValue.class);
```


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fjustdb%2Fjackson-dataformat-toml.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fjustdb%2Fjackson-dataformat-toml?ref=badge_large)