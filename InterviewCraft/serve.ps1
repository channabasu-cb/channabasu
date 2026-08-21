param(
    [int]$Port = 3000,
    [string]$Path = "$PSScriptRoot\interviewcraft-frontend"
)

$listener = New-Object System.Net.HttpListener
$listener.Prefixes.Add("http://localhost:$($Port)/")
$listener.Prefixes.Add("http://127.0.0.1:$($Port)/")

try {
    $listener.Start()
    Write-Host "========================================================" -ForegroundColor Green
    Write-Host " 🚀 InterviewCraft AI Platform Server Running!" -ForegroundColor Yellow
    Write-Host " Local URL: http://localhost:$($Port)" -ForegroundColor Cyan
    Write-Host " Root Directory: $Path" -ForegroundColor Gray
    Write-Host "========================================================" -ForegroundColor Green
} catch {
    Write-Error "Failed to start HTTP listener on port $($Port): $($_.Exception.Message)"
    exit 1
}

$mimeTypes = @{
    ".html" = "text/html; charset=utf-8"
    ".htm"  = "text/html; charset=utf-8"
    ".css"  = "text/css; charset=utf-8"
    ".js"   = "application/javascript; charset=utf-8"
    ".json" = "application/json; charset=utf-8"
    ".png"  = "image/png"
    ".jpg"  = "image/jpeg"
    ".jpeg" = "image/jpeg"
    ".svg"  = "image/svg+xml"
    ".ico"  = "image/x-icon"
}

while ($listener.IsListening) {
    try {
        $context = $listener.GetContext()
        $request = $context.Request
        $response = $context.Response

        $response.AddHeader("Access-Control-Allow-Origin", "*")
        $response.AddHeader("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, PATCH, PUT")
        $response.AddHeader("Access-Control-Allow-Headers", "*")

        if ($request.HttpMethod -eq "OPTIONS") {
            $response.StatusCode = 200
            $response.Close()
            continue
        }

        $urlPath = $request.Url.LocalPath.TrimStart('/')
        if ([string]::IsNullOrWhiteSpace($urlPath) -or $urlPath -eq "/") {
            $urlPath = "index.html"
        }

        $filePath = Join-Path $Path ($urlPath -replace '/', '\')

        if (Test-Path $filePath -PathType Leaf) {
            $ext = [System.IO.Path]::GetExtension($filePath).ToLower()
            $contentType = if ($mimeTypes.ContainsKey($ext)) { $mimeTypes[$ext] } else { "application/octet-stream" }
            $response.ContentType = $contentType
            $bytes = [System.IO.File]::ReadAllBytes($filePath)
            $response.ContentLength64 = $bytes.Length
            $response.StatusCode = 200

            if ($request.HttpMethod -ne "HEAD") {
                $response.OutputStream.Write($bytes, 0, $bytes.Length)
            }
        } else {
            $response.StatusCode = 404
            $notFound = [System.Text.Encoding]::UTF8.GetBytes("<h1>404 Not Found</h1><p>$urlPath does not exist.</p>")
            $response.ContentLength64 = $notFound.Length
            if ($request.HttpMethod -ne "HEAD") {
                $response.OutputStream.Write($notFound, 0, $notFound.Length)
            }
        }
        $response.OutputStream.Flush()
        $response.OutputStream.Close()
        $response.Close()
    } catch {
        # continue on client disconnects
    }
}
