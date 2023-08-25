#! /bin/sh
version=$(
 awk '
  /<dependenc/{exit}
  /<parent>/{parent++};
  /<version>/{
    if (parent == 1) {
      sub(/.*<version>/, "");
      sub(/<.*/, "");
      parent_version = $0;
    } else {
      sub(/.*<version>/, "");
      sub(/<.*/, "");
      version = $0;
      exit
    }
  }
  /<\/parent>/{parent--};
  END {
    print (version == "") ? parent_version : version
  }' pom.xml
)
APP_NAME="registry.gitlab.com/smiraj250789/power-ledger.challenge"
echo "${APP_NAME}"
echo "${version}"
docker build -t "${APP_NAME}:${version}" .
docker tag  "${APP_NAME}:${version}" "${APP_NAME}:${CI_COMMIT_BRANCH}-latest"
# docker push "${APP_NAME}:${version}"
docker push "${APP_NAME}:${CI_COMMIT_BRANCH}-latest"
