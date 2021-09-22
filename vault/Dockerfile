FROM alpine:3.14
WORKDIR /usr/local/bin
RUN wget https://releases.hashicorp.com/vault/1.8.2/vault_1.8.2_linux_amd64.zip
RUN unzip vault_1.8.2_linux_amd64.zip
RUN rm ./vault_1.8.2_linux_amd64.zip
expose 8200/tcp
ENTRYPOINT ["vault", "server", "-dev"]
