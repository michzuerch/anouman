FROM gitpod/workspace-full:latest

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 12.0.2.hs-adpt"
