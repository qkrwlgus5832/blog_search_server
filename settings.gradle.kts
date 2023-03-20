rootProject.name = "kakaobank-blog"

include("api")
include("domain")
include("client")
include("common")
include("common:http")
findProject(":common:http")?.name = "http"
