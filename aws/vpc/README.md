# VPC - Amazon Virtual Private Cloud

- リージョンとは、AWSの各サービスが提供されている地域のこと
- アベイラビリティゾーンとは、独立したデータセンターのこと
  - 複数のデータセンターを束ねたものがアベイラビリティゾーンになり、どのリージョンにも二つ以上存在する
- VPCは、AWS上に仮想ネットワークを作成できるサービス
  - VPCは複数のアベイラビリティゾーンに跨って作成される
  - サブネットは、VPCを細かく区切ったネットワーク
  - サブネットはアベイラビリティゾーンに作成する

## VPC設計ポイント

- プライベートIPアドレス範囲から指定する
  - VPCでは仮想のプライベートネットワーク空間を作成するのでプライベートIPアドレスの使用が推奨

  |クラス|範囲|
  |-|-|
  |A|10.0.0.0〜10.255.255.255|
  |B|172.16.0.0〜172.31.255.255|
  |C|192.168.0.0〜192.168.255.255|

- 作成後は変更できないので、大きめに設定する
  - 大きさは/28から/16。/16が推奨
- オンプレミスや他VPCのレンジと重複しないよう注意
  - 相互接続する場合は重複しないよう設計
- 異なるシステムの場合はアカウントを分ける
  - 異なるシステムを同一のアカウント内に入れると管理が煩雑になる
- 同一システムの各環境は、VPCとアカウントのどちらを分けるか
  - 環境が違う場合、アカウントもVPCも同一のものを使用するのはダメ
  - VPCを分けるとIAMの設定が一度で良い。反面、各環境のリソースが見えてしまい事故の元に
  - アカウントを分けると、他の環境のリソースが見えず、作業がしやすい。反面、環境ごとにIAMの設定が必要
  - 同一アカウントで、VPCとリージョンを分けると良い
- 将来的に必要なIPアドレス数を見積もって設定する
  - /24が標準的
- サブネットの分割は、ルーティングとアベイラビリティゾーンを基準に行う
  - サブネットに割り当てられるルートテーブルは一つ
  - インターネットアクセスの有無、拠点アクセスの有無などのルーティングポリシーに応じて分割する
  - 高可用性のために、二つ以上のアベイラビリティゾーンを使用する