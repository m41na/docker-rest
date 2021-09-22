## Create Self Signed Server Cert:
keytool -genkey -alias rest-server -keyalg RSA -keysize 2048 -storetype JKS -keystore rest-server-keystore.jks -validity 365 -ext SAN=dns:localhost,ip:127.0.0.1
*That last part in key tool command is very critical as self signed cert created without SAN entries won’t work with Chrome and Safari.*

## The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format
keytool -importkeystore -srckeystore rest-server-keystore.jks -destkeystore rest-server-keystore.jks -deststoretype pkcs12

## Or post-java 8
keytool -genkeypair -alias rest-server -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore rest-server-keystore.p12 -validity 365 -ext SAN=dns:localhost,ip:127.0.0.1

## Create Public Certificate File From Server Cert:
keytool -export -alias rest-server -file rest-server.cert-keystore rest-server-keystore.p12

## View content of keystore
keytool -list -keystore rest-server-keystore.p12

## Import Client Cert to Server's p12 File - The certificate file generated for the client step must be transferred to the server host and imported into the truststore of the server.
keytool -importcert -alias rest-client -file ~/Projects/wfh/eng-mgr/rest-client/src/main/resources/secrets/rest-client.cert-keystore rest-server-keystore.p12

## Export a single public key certificate out of a p12 file and into PEM format
keytool -exportcert -alias rest-client -rfc -file rest-client.pem -keystore rest-server-keystore.p12

** Alternatively **
## Creating a CA-Signed Certificate Request - This is if you are not using a self-signed certificate
keytool -certreq -alias rest-server -keystore rest-server-keystore.p12 -file rest-server.csr

## Importing a CA-Signed Certificate
keytool -import -trustcacerts -alias rest-server -file <certificate_file> -keystore rest-server-keystore.p12