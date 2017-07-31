var files;

$(function () {
    $("#fileupload").change(function (event) {
        $("#imgCon").html("");
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp|.xml|.muf)$/;
        if (regex.test($(this).val().toLowerCase())) {
            if ($.browser.msie && parseFloat(jQuery.browser.version) <= 9.0) {
                $("#imgCon").show();
                $("#imgCon")[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = $(this).val();
            } else {
                if (typeof(FileReader) != "undefined") {
                    $("#imgCon").show();
                    $("#imgCon").append("<img />");
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#imgCon img").attr("src", e.target.result);
                        $("#imgCon img").attr("id", "currentImg");
                    };
                    reader.readAsDataURL($(this)[0].files[0]);
                } else {
                    alert("This browser does not support FileReader.");
                }
            }
        } else {
            alert("Please upload a valid image file.");
        }

        // Add files to form before sending.
        files = event.target.files;
    });
});

/**
 * Send file when clicked.
 */
$("#submit").click(function (event) {

    var data = new FormData();
    $.each(files, function (i, file) {
        data.append('file-' + i, file);
    });

    $.ajax({
        url: 'http://deco3800-deathbydeco.uqcloud.net/behind/dataset/post',
        type: 'POST',
        data: data,
        cache: false,
        maxFileSize: 1024 * 1024,
        crossDomain: true,
        contentType: false,
        processData: false,
        success: function (data) {
            alert("File has been successfully uploaded to the server.");
        },
        error: function (data) {
            console.log('ERRORS: ' + data);
        }
    });
});

/**
 * Function used to get images from the server.
 */
$("#getimage-button").click(function () {
    $("#imgCon").show();
    $("#imgCon").append("<img />");
    $("#imgCon img").attr("src", 'http://deco3800-deathbydeco.uqcloud.net/behind/dataset/get');
    $("#imgCon img").attr("id", "currentImg");
});
