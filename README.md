## d1_libclient_java: DataONE Java Client Library

- **Authors**: Jones, Matthew B.; Leinfelder, Ben; Berkley, Chad; Nahf, Rob; Tao, Jing; Jones, Chris; Walker, Lauren
- **License**: [Apache 2](http://opensource.org/licenses/Apache-2.0)
- [Package source code on GitHub](https://github.com/DataONEorg/d1_libclient_java)
- [**Submit Bugs and feature requests**](https://github.com/DataONEorg/d1_libclient_java/issues)
- Contact us: support@dataone.org
- [DataONE discussions](https://github.com/DataONEorg/dataone/discussions)

**d1_libclient_java** is a client library for calling DataONE services. The library
exposes the DataONE services as a set of Java classes and method calls, and
forwards these requests to particular DataONE rest services.  The response
and exceptions are handed back to the calling application.  The library
makes it easy to utilize DataONE services without having to have a complete
understanding of the REST API.

See the test classes under src/test for example usage.

DataONE projects in general are open source, community projects.  We [welcome contributions](./CONTRIBUTING.md) in many forms, including code, graphics, documentation, bug reports, testing, etc.  Use the [DataONE discussions](https://github.com/DataONEorg/dataone/discussions) to discuss these contributions with us.

## Documentation

- Javadoc APIs are available for the library
- [DataONE API](https://search.dataone.org/api) documentation

## Build and test

This a Java project configured with Apache Maven. You can build the source and run the tests with:

```bash
$ mvn clean test
```

## License
```
Copyright [2008-2026] DataONE Participating institutions

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Acknowledgements
Work on this package was supported by:

- DataONE Network
- Arctic Data Center: NSF-PLR grant #2042102 to M. B. Jones, A. Budden, M. Schildhauer, and J. Dozier

Additional support was provided for collaboration by the National Center for Ecological Analysis and Synthesis, a Center funded by the University of California, Santa Barbara, and the State of California.

[![DataONE_footer](https://user-images.githubusercontent.com/6643222/162324180-b5cf0f5f-ae7a-4ca6-87c3-9733a2590634.png)](https://dataone.org)

[![nceas_footer](https://www.nceas.ucsb.edu/sites/default/files/2020-03/NCEAS-full%20logo-4C.png)](https://www.nceas.ucsb.edu)
