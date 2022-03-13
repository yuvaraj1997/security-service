#!bin/bash

export SYMMETRIC_SECRET_KEY="D5V267JQ668E3EGQ"
export INIT_VECTOR_KEY="D5AAZSUYDZ7BVJ88"
export ASYMMETRIC_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW7rcDycZExn2e3vGd4C7290LOkv2KImKY1Ysxa1iaLEwn42P9MonsZV0J0OeSW2zRg534XWve0VbTrYhfT6cexepy8167FfFpfggl6MuaQcwDIILiFRaPyP9LicFg+TY3yGqhQMPg9HD/bLAQI7IffQvu3wOEh1h5UvNIF0LvkQIDAQAB"
export ASYMMETRIC_PRIVATE_KEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJbutwPJxkTGfZ7e8Z3gLvb3Qs6S/YoiYpjVizFrWJosTCfjY/0yiexlXQnQ55JbbNGDnfhda97RVtOtiF9Ppx7F6nLzXrsV8Wl+CCXoy5pBzAMgguIVFo/I/0uJwWD5NjfIaqFAw+D0cP9ssBAjsh99C+7fA4SHWHlS80gXQu+RAgMBAAECgYAmmmOgymi1inbyvV7a3E3myJMDtsC2JdlF8cgqGaSNuiq4QQZ/6f8wwTQsoIu5+Tw50goDThGa6bvlZArvJbY+Whi+eHiczhlOW8nvLMnBld9yUzyRGWRhXeevOB3m+u9VhWQkqZMtiww+7XvapVbVLl61zoA++lHQJVbVheMwqQJBAMkVvCw4L6pbAA00WbwEyggb3TJn6v0NVlR+8eGUBEj6yJsEfrjy5P+jiNV5lzZEmgTFwBDHb3jloJi9eEOXO9cCQQDAJrgqGcmpInR7PSIt7FE5RnVeMWFcjpqlU8R6ETaOG7iogGcrip8QinpSQpjMapTBL/lFkwWnKbbN9UGoeALXAkEAkcRjyFpik0esXeYZNqbyHba1Ppj+S7qjXlU32qKGCLFjQpybkE7jmj1eMAIYj4pjbBo+BOvN7wHmzVJsH/M9uQJABX3Vy5Y7FjNe4trHbEqRs7JYzl1Y6/1DXDA9sENnrL4ME/y/dhYbH6zlfA5dMZJmN/M4C439Hl7p048L1lW/twJBALWDY4pvjcb5ih2L7tuPsKuqGoXpn9b67c6F2baGKB5ywho0eOGZD3/DUMxiE0PhkONLQIk0FtN/B0nKQ+Qm2VA="
export REFRESH_TOKEN_EXPIRY="-1"
export SESSION_TOKEN_EXPIRY="30"

java -jar target/security-service-0.0.1-SNAPSHOT.jar refresh 1234 dev

$SHELL