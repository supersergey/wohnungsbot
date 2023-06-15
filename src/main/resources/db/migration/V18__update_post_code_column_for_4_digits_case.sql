update main.post_code
    set id= concat('0', id)
where length(post_code.id) = 4;

