<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        Admin - <sitemesh:write property="title"/>
    </title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <script
        src="https://code.jquery.com/jquery-3.6.4.min.js">
    </script>

    <script>
        var contextPath =
            "${pageContext.request.contextPath}";
    </script>

    <style>

        html,
        body {
            min-height: 100%;
        }

        body {
            background: #f5f7fb;
        }

        .app-main {
            min-height: calc(100vh - 140px);
        }

        .admin-card {
            background: #fff;
            border-radius: 16px;
            padding: 24px;
            box-shadow:
                0 .25rem 1rem rgba(0, 0, 0, .07);
        }

        img {
            max-width: 100%;
        }

    </style>

    <sitemesh:write property="head"/>

</head>

<body>

    <%@ include file="/commons/web/header.jsp" %>

    <main class="app-main py-4">

        <div class="container">

            <div class="admin-card">

                <sitemesh:write property="body"/>

            </div>

        </div>

    </main>

    <%@ include file="/commons/web/footer.jsp" %>

    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js">
    </script>

</body>

</html>