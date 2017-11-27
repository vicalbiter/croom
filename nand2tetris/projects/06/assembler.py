""" Nand2Tetris Project 06 - Python Assembler"""
symbol_table = {'SP':0, 'LCL':1, 'ARG':2, 'THIS':3, 'THAT':4, 'SCREEN':16384,
'KBD':24576, 'R0':0, 'R1':1, 'R2':2, 'R3':3, 'R4':4, 'R5':5, 'R6':6, 'R7':7,
'R8':8, 'R9':9, 'R10':10, 'R11':11, 'R12':12, 'R13':13, 'R14':14, 'R15':15}

def translate_file(filename_source, filename_dest, symbol_table=symbol_table):
    split_file = read_file(filename_source)
    split_file_nw = delete_whitespace(split_file)
    symbol_table = update_symbol_table(split_file_nw, symbol_table)
    split_file_ns = replace_symbols(split_file_nw, symbol_table)
    final_file = write_to_file(filename_dest, split_file_ns)
    return final_file, split_file_ns

def read_file(filename):
    asm_file = open(filename, "r")
    return asm_file.read().replace(' ', '').split("\n")

def write_to_file(filename, lines_file):
    #f = open(filename, 'w');
    file_hack = ""
    for line in lines_file:
        if translate_line(line) != None:
            file_hack += (translate_line(line) + '\n')
    f = open(filename, 'w')
    f.write(file_hack)
    f.close()
    return file_hack

def delete_whitespace(lines_file):
    """Delete all whitespaces and comments inside a given string"""
    lines_file_nw = []
    for line in lines_file:
        if line != '' and line[:2] != '//':
            if line.find("//") != -1:
                lines_file_nw.append(line.split("//")[0])
            else:
                lines_file_nw.append(line)
    return lines_file_nw

def update_symbol_table(lines_file, symbol_table):
    line_count = 0
    variable_count = 16
    for line in lines_file:
        if line[0] == '(':
            symbol_table[line.replace('(', '').replace(')','')] = line_count
        else:
            line_count += 1
    for line in lines_file:
        if line[0] == '@':
            if not line[1].isnumeric():
                if line[1:] not in symbol_table:
                    symbol_table[line[1:]] = variable_count
                    variable_count += 1
    return symbol_table

def replace_symbols(lines_file, symbol_table):
    file_ns = []
    for line in lines_file:
        if line[1:] in symbol_table:
            line = '@' + str(symbol_table[line[1:]])
        file_ns.append(line)
    return file_ns

def command_type(command):
    if command[0] == '@':
        return 'A_COMMAND'
    elif command[0] == '(':
        return 'L_COMMAND'
    else:
        return 'C_COMMAND'

def translate_a_command(command):
    translated_command = bin(int(command[1:]))[2:]
    bits = 16 - len(translated_command)
    while bits > 0:
        translated_command = '0' + translated_command
        bits -= 1
    return translated_command

def translate_c_command(command):
    dest_cmd = command.find('=') != -1
    jump_cmd = command.find(';') != -1
    if jump_cmd and dest_cmd:
        c_parts = command.replace(';', '=').split('=')
        return '111' + m_or_a(c_parts[1]) + comp(c_parts[1]) + dest(c_parts[0]) + jump(c_parts[2])
    elif jump_cmd and not dest_cmd:
        c_parts = command.split(';')
        return '111' + m_or_a(c_parts[0]) + comp(c_parts[0]) + '000' + jump(c_parts[1])
    elif not jump_cmd and dest_cmd:
        c_parts = command.split('=')
        return '111' + m_or_a(c_parts[1]) + comp(c_parts[1]) + dest(c_parts[0]) + '000'

def m_or_a(cpart):
    if cpart.find('M') != -1:
        return '1'
    else:
        return '0'

def comp(cpart):
    if cpart == '0':
        return '101010'
    elif cpart == '1':
        return '111111'
    elif cpart == '-1':
        return '111010'
    elif cpart == 'D':
        return '001100'
    elif cpart == 'A' or cpart == 'M':
        return '110000'
    elif cpart == '!D':
        return '001101'
    elif cpart == '!A' or cpart == '!M':
        return '110001'
    elif cpart == '-D':
        return '001111'
    elif cpart == '-A' or cpart == '-M':
        return '110011'
    elif cpart == 'D+1':
        return '011111'
    elif cpart == 'A+1' or cpart == 'M+1':
        return '110111'
    elif cpart == 'D-1':
        return '001110'
    elif cpart == 'A-1' or cpart == 'M-1':
        return '110010'
    elif cpart == 'D+A' or cpart == 'D+M':
        return '000010'
    elif cpart == 'D-A' or cpart == 'D-M':
        return '010011'
    elif cpart == 'A-D' or cpart == 'M-D':
        return '000111'
    elif cpart == 'D&A' or cpart == 'D&M':
        return '000000'
    else:
        return '010101'

def dest(cpart):
    if cpart == 'M':
        return '001'
    elif cpart == 'D':
        return '010'
    elif cpart == 'MD':
        return '011'
    elif cpart == 'A':
        return '100'
    elif cpart == 'AM':
        return '101'
    elif cpart == 'AD':
        return '110'
    else:
        return '111'

def jump(cpart):
    if cpart == 'JGT':
        return '001'
    elif cpart == 'JEQ':
        return '010'
    elif cpart == 'JGE':
        return '011'
    elif cpart == 'JLT':
        return '100'
    elif cpart == 'JNE':
        return '101'
    elif cpart == 'JLE':
        return '110'
    else:
        return '111'

def translate_l_command(command):
    return None

def translate_line(command):
    ctype = command_type(command)
    if ctype == 'A_COMMAND':
        return translate_a_command(command)
    elif ctype == 'L_COMMAND':
        return translate_l_command(command)
    else:
        return translate_c_command(command)

# Read and split the file into lines
#translated_file, split_file_ns = translate_file('max/Max.asm', 'Max.hack', symbol_table)
#translated_file, split_file_ns = translate_file('add/Add.asm', 'Add.hack', symbol_table)
#translated_file, split_file_ns = translate_file('pong/Pong.asm', 'Pong.hack', symbol_table)
translated_file, split_file_ns = translate_file('rect/Rect.asm', 'Rect.hack', symbol_table)
#split_file = read_file('max/Max.asm')
#split_file_nw = delete_whitespace(split_file)
#symbol_table = update_symbol_table(split_file_nw, symbol_table)
#split_file_ns = replace_symbols(split_file_nw, symbol_table)
#final_file = write_to_file(filename_dest, split_file_ns)
