var query = $('#fileupload').fileupload({
    url: 'http://deco3800-deathbydeco.uqcloud.net/behind/dataset/post',
    // url: 'http://localhost:1337/dataset/post',
    type: 'POST',
    dataType: 'json',
    singleFileUploads: false,
    add: function (e, data) {
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.xml|.muf)$/;
        var $inputs = $('#inputs');
        var innerHTML = "";
        var files = [];
        $.each(data.files, function (i, file) {
            if (regex.test(file.name)) {
                files.push(file);
                innerHTML += "<p>File " + file.name + "</p>";
            }
        });
        $inputs.html(innerHTML);
        data.files = files;

        $("#query").click(function () {
            var fileNames = [];
            $.each(data.files, function (i, file) {
                fileNames.push(file.name);
            });
            data.paramName = fileNames;
            data.submit();
        });
    },
    progressall: function (e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#upload-progress').text(progress);
        $('#progress').find('.bar').css('width', progress + '%');
    },
    done: function (e, data) {
        var status = data._response.result.status;
        var $outputs = $('#outputs');
        if (status === 'successful') {
            var responses = data._response.result.data;
            var innerHTML = "";
            $.each(responses, function (i, response) {
                innerHTML += "<h1>Result for " + response.name + "</h1>";
                innerHTML += "<p>" + response.type + "</p>";
                innerHTML += "<br>";
            });
            $outputs.html(innerHTML);
        } else {
            $outputs.html("Errors when querying the neural network. Please" +
                " try again.");
        }
    },
    fail: function (e, data) {
        console.log(data);
        if (data.errorThrown === 'abort') {
            alert('File Upload has been canceled');
        } else {
            alert('File Upload failed due to unexpected reason. Please try' +
                ' again.');
        }
    }
});

$('button#cancel').click(function (e) {
    query.abort();
});
