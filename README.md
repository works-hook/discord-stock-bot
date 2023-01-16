# discord stock bot

### introduce
> JDA를 이용해서 만든 디스코드 주식 봇입니다<br>
> 주로 커멘드 명령어로 사용할 수 있습니다<br>
> NAVER 주식 페이지 API를 사용해 임베드를 구성했습니다

### stack
- project
  - build
    - gradle 
  - language
    - kotlin 1.6.20
  - compiler
    - jvm 11
  - plugin
    - JDA (Java Discord API) 5.0.0 beta 2
    - GSON 2.10

### command
- ping
  - heath check를 하는 명령어입니다.
- exchange
  - 나라를 선택해 해당 나라의 환률을 조회하는 명령어입니다. 옵션을 선택하지 않으면 기본 환률 정보들을 조회합니다.
  - options (필수 : X)
    - 미국 USD
    - 일본 JPY
    - 유럽연합 EUR
    - 중국 CNY
- top-search
  - 현재 인기 종목을 조회하는 명령어입니다.
- top-global
  - 나라를 선택해 해당 나라의 top 종목을 조회하는 명령어 입니다.
  - options (필수 : O)
    - 미국
    - 중국
    - 홍콩
    - 일본
    - 베트남
- main-news
  - 주식 관련 주요 뉴스를 조회하는 명령어입니다.

### 봇 설치 링크
https://discord.com/api/oauth2/authorize?client_id=1055719276276887583&permissions=2147485696&scope=applications.commands%20bot

### 나중에 참고하면 좋을것같은 사이트
- 임베드 메세지 포멧 사이트 : https://autocode.com/tools/discord/embed-builder/
- JDA API : https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/package-summary.html
- 여러가지 예제 코드들 : https://www.tabnine.com/code/java/classes/net.dv8tion.jda.core.EmbedBuilder
