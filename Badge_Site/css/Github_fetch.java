<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GitHub Badge Checker</title>
</head>
<body>

<h2>GitHub Badge Checker</h2>

<p>
    <label for="repo-url">GitHub Repository URL:</label>
    <input type="url" id="repo-url" placeholder="https://github.com/user/repo">
    <button onclick="fetchBadges()">Check Badges</button>
</p>

<ul id="badge-list"></ul>

<script>
function determineCategory(badgeText, badgeUrl) {
    if (badgeText.includes('build') || badgeUrl.includes('travis-ci')) {
        return 'CI';
    }
    // その他のカテゴリについても同様に判断を追加
    // ...
    return 'Unknown';
}

function fetchBadges() {
    const repoUrl = document.getElementById('repo-url').value;
    const rawUrl = repoUrl.replace("github.com", "raw.githubusercontent.com").replace("/blob", "") + "/master/README.md";
    
    fetch(rawUrl)
        .then(response => response.text())
        .then(data => {
            const badgeRegex = /\[!\[([^\]]+)\]\(([^\)]+)\)\]\([^\)]+\)/g;
            let match;
            const badgeList = document.getElementById('badge-list');
            badgeList.innerHTML = '';
            
            while (match = badgeRegex.exec(data)) {
                const badgeText = match[1];
                const badgeUrl = match[2];
                const category = determineCategory(badgeText, badgeUrl);

                const listItem = document.createElement('li');
                listItem.textContent = `Badge: ${badgeText} - Category: ${category}`;
                badgeList.appendChild(listItem);
            }
        })
        .catch(error => {
            console.error('Error fetching the README:', error);
        });
}
</script>

</body>
</html>
