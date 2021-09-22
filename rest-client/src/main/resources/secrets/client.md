## Create A Self Signed Client Cert
keytool -genkey -alias rest-client -keyalg RSA -keysize 2048 -storetype JKS -keystore rest-client-keystore.jks -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1
*That last part in key tool command is very critical as self signed cert created without SAN entries won’t work with Chrome and Safari.*

## The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format
keytool -importkeystore -srckeystore rest-client-keystore.jks -destkeystore rest-client-keystore.jks -deststoretype pkcs12

## Or post-java 8
keytool -genkeypair -alias rest-client -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore rest-client-keystore.p12 -validity 365 -ext SAN=dns:localhost,ip:127.0.0.1

## Create public certificate file from client cert
keytool -export -alias rest-client -file rest-client.cert -keystore rest-client-keystore.p12

## View content of keystore
keytool -list -keystore rest-client-keystore.p12

## Import Server Cert to Client's p12 File - The certificate file generated for the server step must be transferred to the client host and imported into the truststore of the client.
keytool -importcert -alias rest-server -file ~/Projects/wfh/eng-mgr/rest-jwt/src/main/resources/secrets/rest-server.cert -keystore rest-client-keystore.p12

## Export a single public key certificate out of a p12 file and into PEM format
keytool -exportcert -alias rest-server -rfc -file rest-server.pem -keystore rest-client-keystore.p12

** Alternatively **
## Creating a CA-Signed Certificate Request - This is if you are not using a self-signed certificate
keytool -certreq -alias rest-client -keystore rest-client-keystore.p12 -file rest-client.csr

## Importing a CA-Signed Certificate
keytool -import -trustcacerts -alias rest-client -file <certificate_file> -keystore rest-client-keystore.p12
