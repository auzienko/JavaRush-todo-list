    function editTask(id) {
        let deleteIdentifier = "#delete_" + id;
        $(deleteIdentifier).remove();

        let editIdentifier = "#edit_" + id;
        let saveTag = "✅ Save";
        $(editIdentifier).html(saveTag);
        let saveTagProperty="updateTask(" + id +")";
        $(editIdentifier).attr("onclick", saveTagProperty);

        let currentTrElement = $(editIdentifier).parent().parent();
        let children = currentTrElement.children();
        let description = children[1];
        description.innerHTML = "<input id='input_description_" + id + "' type='text' value='" + description.innerHTML + "'>";

        let status = children[2];
        let statusId = "#select_" + id;
        let statusCurrentValue = status.innerHTML;
        console.log($(statusId));
        console.log(statusCurrentValue);
        status.innerHTML = getStatusDropdown(id);
        $(statusId).val(statusCurrentValue).change();
    }

    function getStatusDropdown(id) {
        return "<select id='select_" + id + "'>" +
        "<option value='IN_PROGRESS'>IN_PROGRESS</option>" +
        "<option value='DONE'>DONE</option>" +
        "<option value='PAUSED'>PAUSED</option>" +
        "</select>";
    }

    function updateTask(id) {
            let url = "/" + id;
            let type = "POST";

            let description = $("#input_description_" + id).val();
            let status = $("#select_" + id).val();

            console.log(status);


        $.ajax({
            url: url,
            type: type,
            async: false,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({"description": description, "status": status})
        });

        setTimeout(
            () => {
                document.location.reload();
            }, 300);
    }

    function deleteTask(id) {
            let url = "/" + id;
            let type = "DELETE";
        $.ajax({
            url: url,
            type: type,
            success: function (response) {
                window.location.reload();
            }
        });
    }
