<html>
<header>
    <meta charset="UTF-8" />
    <title>成功</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</header>

<body>

<div class="alert alert-dismissable alert-success">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
    <h4>
        成功!
    </h4> <strong>${msg}</strong><a href="${url}" class="alert-link">3s后自动跳转</a>
</div>

<script>
    setTimeout('location.href="${url}"', 3000);
</script>

</body>
</html>